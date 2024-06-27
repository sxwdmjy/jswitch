
package com.jswitch.sip.parse;

import com.jswitch.sip.header.SIPHeader;
import com.jswitch.sip.header.Server;
import lombok.extern.slf4j.Slf4j;

import java.text.ParseException;

@Slf4j
public class ServerParser extends HeaderParser {


    public ServerParser(String server) {
        super(server);
    }


    protected ServerParser(Lexer lexer) {
        super(lexer);
    }

    public SIPHeader parse() throws ParseException {

        if (log.isDebugEnabled())
            dbg_enter("ServerParser.parse");
        Server server = new Server();
        try {
            headerName(TokenTypes.SERVER);
            if (this.lexer.lookAhead(0) == '\n')
                throw createParseException("empty header");

            while (this.lexer.lookAhead(0) != '\n'
                && this.lexer.lookAhead(0) != '\0') {
                if (this.lexer.lookAhead(0) == '(') {
                    String comment = this.lexer.comment();
                    server.addProductToken('(' + comment + ')');
                } else {
                    String tok;
                    int marker = 0;
                    try {
                        marker = this.lexer.markInputPosition();
                        tok = this.lexer.getString('/');

                        if (tok.charAt(tok.length() - 1) == '\n')
                            tok = tok.trim();
                        server.addProductToken(tok);
                    } catch (ParseException ex) {
                        this.lexer.rewindInputPosition(marker);
                        tok = this.lexer.getRest().trim();
                        server.addProductToken(tok);
                        break;
                    }
                }
            }

        } finally {
            if (log.isDebugEnabled())
                dbg_leave("ServerParser.parse");
        }

        return server;
    }

}

