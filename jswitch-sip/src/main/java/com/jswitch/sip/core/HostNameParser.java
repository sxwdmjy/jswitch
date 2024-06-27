package com.jswitch.sip.core;

import com.jswitch.sip.Host;
import com.jswitch.sip.HostPort;
import lombok.extern.slf4j.Slf4j;

import java.text.ParseException;

import static com.jswitch.sip.core.LexerCore.*;

@Slf4j
public class HostNameParser extends ParserCore {
	

    private static boolean stripAddressScopeZones = false;
    
    static {
    	stripAddressScopeZones = Boolean.getBoolean("gov.nist.core.STRIP_ADDR_SCOPES");
    }

    public HostNameParser(String hname) {
        this.lexer = new LexerCore("charLexer", hname);
    }

    public HostNameParser(LexerCore lexer) {
        this.lexer = lexer;
        lexer.selectLexer("charLexer");
    }

    private static final char[] VALID_DOMAIN_LABEL_CHAR =
        new char[] {LexerCore.ALPHADIGIT_VALID_CHARS, '-', '.', '_'};
    protected void consumeDomainLabel() throws ParseException {
        if (log.isDebugEnabled())
            dbg_enter("domainLabel");
        try {
            lexer.consumeValidChars(VALID_DOMAIN_LABEL_CHAR);
        } finally {
            if (log.isDebugEnabled())
                dbg_leave("domainLabel");
        }
    }

    protected String ipv6Reference() throws ParseException {
        StringBuilder retval = new StringBuilder();
        if (log.isDebugEnabled())
            dbg_enter("ipv6Reference");

        try {

            if(stripAddressScopeZones){
                while (lexer.hasMoreChars()) {
                    char la = lexer.lookAhead(0);
                    //'%' is ipv6 address scope zone. see detail at
                    if (LexerCore.isHexDigit(la) || la == '.' || la == ':'
                            || la == '[' ) {
                        lexer.consume(1);
                        retval.append(la);
                    } else if (la == ']') {
                        lexer.consume(1);
                        retval.append(la);
                        return retval.toString();
                    } else if (la == '%'){
                        //we need to strip the address scope zone.
                        lexer.consume(1);

                        String rest = lexer.getRest();

                        if(rest == null || rest.length() == 0){
                            //head for the parse exception
                            break;
                        }

                        //we strip everything until either the end of the string
                        //or a closing square bracket (])
                        int stripLen = rest.indexOf(']');

                        if (stripLen == -1){
                            //no square bracket -> not a valid ipv6 reference
                            break;
                        }

                        lexer.consume(stripLen+1);
                        retval.append("]");
                        return retval.toString();

                    } else
                        break;
                }
            }
            else
            {
                while (lexer.hasMoreChars())
                {
                    char la = lexer.lookAhead(0);
                    if (LexerCore.isHexDigit(la) || la == '.'
                            || la == ':' || la == '[') {
                        lexer.consume(1);
                        retval.append(la);
                    } else if (la == ']') {
                        lexer.consume(1);
                        retval.append(la);
                        return retval.toString();
                    } else
                    break;
                }
            }

            throw new ParseException(
                lexer.getBuffer() + ": Illegal Host name ",
                lexer.getPtr());
        } finally {
            if (log.isDebugEnabled())
                dbg_leave("ipv6Reference");
        }
    }

    public Host host() throws ParseException {
        if (log.isDebugEnabled())
            dbg_enter("host");
        try {
            String hostname;

            //IPv6 referene
            if (lexer.lookAhead(0) == '[') {
                hostname = ipv6Reference();
            }
            //IPv6 address (i.e. missing square brackets)
            else if( isIPv6Address(lexer.getRest()) )
            {
                int startPtr = lexer.getPtr();
                lexer.consumeValidChars(
                        new char[] {LexerCore.ALPHADIGIT_VALID_CHARS, ':'});
                hostname
                    = new StringBuilder("[").append(
                        lexer.getBuffer().substring(startPtr, lexer.getPtr()))
                        .append("]").toString();
            }
            //IPv4 address or hostname
            else {
                int startPtr = lexer.getPtr();
                consumeDomainLabel();
                hostname = lexer.getBuffer().substring(startPtr, lexer.getPtr());
            }

            if (hostname.length() == 0)
                throw new ParseException(
                    lexer.getBuffer() + ": Missing host name",
                    lexer.getPtr());
            else
                return new Host(hostname);
        } finally {
            if (log.isDebugEnabled())
                dbg_leave("host");
        }
    }


    private boolean isIPv6Address(String uriHeader)
    {
    	String hostName = uriHeader;
    	int indexOfComma = uriHeader.indexOf(",");
    	if(indexOfComma != -1) {
    		hostName  = uriHeader.substring(0, indexOfComma);
    	}
        int hostEnd = hostName.indexOf(QUESTION);

        int semiColonIndex = hostName.indexOf(SEMICOLON);
        if ( hostEnd == -1
            || (semiColonIndex!= -1 && hostEnd > semiColonIndex) )
            hostEnd = semiColonIndex;

        if ( hostEnd == -1 )
            hostEnd = hostName.length();

        String host = hostName.substring(0, hostEnd);

        int firstColonIndex = host.indexOf(COLON);

        if(firstColonIndex == -1)
            return false;

        int secondColonIndex = host.indexOf(COLON, firstColonIndex + 1);

        if(secondColonIndex == -1)
            return false;

        return true;
    }

    public HostPort hostPort(boolean allowWS ) throws ParseException {
        if (log.isDebugEnabled())
            dbg_enter("hostPort");
        try {
            Host host = this.host();
            HostPort hp = new HostPort();
            hp.setHost(host);

            if (allowWS) lexer.SPorHT();
            if (lexer.hasMoreChars()) {
                char la = lexer.lookAhead(0);
                switch (la)
                {
                case ':':
                    lexer.consume(1);
                    if (allowWS) lexer.SPorHT(); // white space before port number should be accepted
                    try {
                        String port = lexer.number();
                        hp.setPort(Integer.parseInt(port));
                    } catch (NumberFormatException nfe) {
                        throw new ParseException(
                            lexer.getBuffer() + " :Error parsing port ",
                            lexer.getPtr());
                    }
                    break;

                case ',':	// allowed in case of multi-headers, e.g. Route
                			// Could check that current header is a multi hdr
                    
                case ';':   // OK, can appear in URIs (parameters)
                case '?':   // same, header parameters
                case '>':   // OK, can appear in headers
                case ' ':   // OK, allow whitespace
                case '\t':
                case '\r':
                case '\n':
                case '/':   // e.g. http://[::1]/xyz.html
                    break;
                case '%':
                    if(stripAddressScopeZones){
                        break;//OK,allow IPv6 address scope zone
                    }
                    
                default:
                    if (!allowWS) {
                        throw new ParseException( lexer.getBuffer() +
                                " Illegal character in hostname:" + lexer.lookAhead(0),
                                lexer.getPtr() );
                    }
                }
            }
            return hp;
        } finally {
            if (log.isDebugEnabled())
                dbg_leave("hostPort");
        }
    }


}
