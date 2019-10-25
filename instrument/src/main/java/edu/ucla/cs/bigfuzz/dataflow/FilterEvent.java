package edu.ucla.cs.bigfuzz.dataflow;

import edu.berkeley.cs.jqf.instrument.tracing.events.TraceEvent;
import edu.berkeley.cs.jqf.instrument.tracing.events.TraceEventVisitor;
import janala.logger.inst.MemberRef;

public class FilterEvent extends TraceEvent {

    public final MemberRef invokedMethod;
    private String str;
    private int arm;

    public FilterEvent(int iid, MemberRef containingMethod, int lineNumber, int arm) {
        super(iid, containingMethod, lineNumber);
//        if(containingMethod!=null)
//            System.out.println(String.format("%s", containingMethod.getName()));
        this.invokedMethod = containingMethod;
//        if(invokedMethod!=null)
//            System.out.println(String.format("%s", invokedMethod.getName()));
        this.arm = arm;
    }

    public String getInvokedMethodName() {
        if (str == null) {
            this.str = invokedMethod.getOwner() + "#" + invokedMethod.getName();
//                    + invokedMethod.getDesc();
        }
        return str;
    }

    public int getArm() {
        return arm;
    }

    @Override
    public String toString() {
        return String.format("FILTER(%d,%d,%d,%s)", iid, lineNumber, getArm(), getInvokedMethodName());
    }


    @Override
    public void applyVisitor(TraceEventVisitor v) {
//        throw new UnsupportedOperationException("No visitor method for custom event");
        v.visitFilterEvent(this);
    }
}
