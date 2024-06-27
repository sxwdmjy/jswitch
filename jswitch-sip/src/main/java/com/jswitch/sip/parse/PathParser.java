package com.jswitch.sip.parse;


import com.jswitch.sip.header.Path;
import com.jswitch.sip.header.PathList;
import com.jswitch.sip.header.SIPHeader;
import lombok.extern.slf4j.Slf4j;

import java.text.ParseException;

@Slf4j
public class PathParser extends AddressParametersParser implements TokenTypes {

    /**
     * Constructor
     */
    public PathParser(String path) {
        super(path);
    }

    protected PathParser(Lexer lexer) {
        super(lexer);
    }

    public SIPHeader parse() throws ParseException {

        PathList pathList = new PathList();

        if (log.isDebugEnabled())
            dbg_enter("PathParser.parse");

        try {
            this.lexer.match(TokenTypes.PATH);
            this.lexer.SPorHT();
            this.lexer.match(':');
            this.lexer.SPorHT();
            while (true) {
                Path path = new Path();
                super.parse(path);
                pathList.add(path);
                this.lexer.SPorHT();
                char la = lexer.lookAhead(0);
                if (la == ',') {
                    this.lexer.match(',');
                    this.lexer.SPorHT();
                } else if (la == '\n')
                    break;
                else
                    throw createParseException("unexpected char");
            }
            return pathList;
        } finally {
            if (log.isDebugEnabled())
                dbg_leave("PathParser.parse");
        }

    }
}
