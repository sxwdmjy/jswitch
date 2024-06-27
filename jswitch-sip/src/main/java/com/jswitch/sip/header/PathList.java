package com.jswitch.sip.header;

public class PathList extends SIPHeaderList<Path> {


    public PathList() {
        super(Path.class, PathHeader.NAME);
    }


    public Object clone() {
        PathList retval = new PathList();
        return retval.clonehlist(this.hlist);
    }

}
