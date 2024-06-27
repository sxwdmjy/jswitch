package com.jswitch.sip.header;

public class ServiceRouteList extends SIPHeaderList<ServiceRoute> {


    private static final long serialVersionUID = -4264811439080938519L;

    public ServiceRouteList() {
        super(ServiceRoute.class, ServiceRouteHeader.NAME);
    }

    public Object clone() {
        ServiceRouteList retval = new ServiceRouteList();
        return retval.clonehlist(this.hlist);
    }

}
