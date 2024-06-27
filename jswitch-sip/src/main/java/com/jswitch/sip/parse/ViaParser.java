package com.jswitch.sip.parse;


import com.jswitch.sip.HostPort;
import com.jswitch.sip.NameValue;
import com.jswitch.sip.ParameterNames;
import com.jswitch.sip.Protocol;
import com.jswitch.sip.core.HostNameParser;
import com.jswitch.sip.core.LexerCore;
import com.jswitch.sip.core.Token;
import com.jswitch.sip.header.SIPHeader;
import com.jswitch.sip.header.ViaList;
import com.jswitch.sip.header.Via;
import lombok.extern.slf4j.Slf4j;

import java.text.ParseException;

import static com.jswitch.sip.ParameterNames.RECEIVED;

@Slf4j
public class ViaParser extends HeaderParser {

    public ViaParser(String via) {
        super(via);
    }

    public ViaParser(Lexer lexer) {
        super(lexer);
    }

    private void parseVia(Via v) throws ParseException {
        // The protocol
        lexer.match(TokenTypes.ID);
        Token protocolName = lexer.getNextToken();

        this.lexer.SPorHT();
        // consume the "/"
        lexer.match('/');
        this.lexer.SPorHT();
        lexer.match(TokenTypes.ID);
        this.lexer.SPorHT();
        Token protocolVersion = lexer.getNextToken();

        this.lexer.SPorHT();

        // We consume the "/"
        lexer.match('/');
        this.lexer.SPorHT();
        lexer.match(TokenTypes.ID);
        this.lexer.SPorHT();

        Token transport = lexer.getNextToken();
        this.lexer.SPorHT();

        Protocol protocol = new Protocol();
        protocol.setProtocolName(protocolName.getTokenValue());
        protocol.setProtocolVersion(protocolVersion.getTokenValue());
        protocol.setTransport(transport.getTokenValue());
        v.setSentProtocol(protocol);

        // sent-By
        HostNameParser hnp = new HostNameParser(this.getLexer());
        HostPort hostPort = hnp.hostPort( true );
        v.setSentBy(hostPort);

        // Ignore blanks
        this.lexer.SPorHT();

        // parameters
        while (lexer.lookAhead(0) == ';') {
            this.lexer.consume(1);
            this.lexer.SPorHT();
            NameValue nameValue = this.nameValue();
            String name = nameValue.getName();
            if (name.equals(ParameterNames.BRANCH)) {
                String branchId = (String) nameValue.getValueAsObject();
                if (branchId == null)
                    throw new ParseException("null branch Id", lexer.getPtr());

            }
            v.setParameter(nameValue);
            this.lexer.SPorHT();
        }


        if (lexer.lookAhead(0) == '(') {
            this.lexer.selectLexer("charLexer");
            lexer.consume(1);
            StringBuilder comment = new StringBuilder();
            while (true) {
                char ch = lexer.lookAhead(0);
                if (ch == ')') {
                    lexer.consume(1);
                    break;
                } else if (ch == '\\') {
                    // Escaped character
                    Token tok = lexer.getNextToken();
                    comment.append(tok.getTokenValue());
                    lexer.consume(1);
                    tok = lexer.getNextToken();
                    comment.append(tok.getTokenValue());
                    lexer.consume(1);
                } else if (ch == '\n') {
                    break;
                } else {
                    comment.append(ch);
                    lexer.consume(1);
                }
            }
            v.setComment(comment.toString());
        }

    }


    protected NameValue nameValue() throws ParseException {
        if (log.isDebugEnabled())
            dbg_enter("nameValue");
        try {

            lexer.match(LexerCore.ID);
            Token name = lexer.getNextToken();
            // eat white space.
            lexer.SPorHT();
            try {

                boolean quoted = false;

                char la = lexer.lookAhead(0);

                if (la == '=') {
                    lexer.consume(1);
                    lexer.SPorHT();
                    String str = null;
                    if (name.getTokenValue().compareToIgnoreCase(RECEIVED) == 0) {
                        // Allow for IPV6 Addresses.
                        // these could have : in them!
                        str = lexer.byteStringNoSemicolon();
                    } else {
                        if (lexer.lookAhead(0) == '\"') {
                            str = lexer.quotedString();
                            quoted = true;
                        } else {
                            lexer.match(LexerCore.ID);
                            Token value = lexer.getNextToken();
                            str = value.getTokenValue();
                        }
                    }
                    NameValue nv = new NameValue(name.getTokenValue()
                            .toLowerCase(), str);
                    if (quoted)
                        nv.setQuotedValue();
                    return nv;
                } else {
                    return new NameValue(name.getTokenValue().toLowerCase(),
                            null);
                }
            } catch (ParseException ex) {
                return new NameValue(name.getTokenValue(), null);
            }

        } finally {
            if (log.isDebugEnabled())
                dbg_leave("nameValue");
        }

    }

    public SIPHeader parse() throws ParseException {
        if (log.isDebugEnabled())
            dbg_enter("parse");
        try {
            ViaList viaList = new ViaList();
            // The first via header.
            this.lexer.match(TokenTypes.VIA);
            this.lexer.SPorHT(); // ignore blanks
            this.lexer.match(':'); // expect a colon.
            this.lexer.SPorHT(); // ingore blanks.

            while (true) {
                Via v = new Via();
                parseVia(v);
                viaList.add(v);
                this.lexer.SPorHT(); // eat whitespace.
                if (this.lexer.lookAhead(0) == ',') {
                    this.lexer.consume(1); // Consume the comma
                    this.lexer.SPorHT(); // Ignore space after.
                }
                if (this.lexer.lookAhead(0) == '\n')
                    break;
            }
            this.lexer.match('\n');
            return viaList;
        } finally {
            if (log.isDebugEnabled())
                dbg_leave("parse");
        }

    }

}
