package edu.ucla.cs.bigfuzz.dataflow;

import edu.berkeley.cs.jqf.instrument.tracing.events.TraceEvent;
import edu.berkeley.cs.jqf.instrument.tracing.events.TraceEventVisitor;
import janala.logger.inst.MemberRef;

public class ReduceByKeyEvent extends TraceEvent {
    protected final MemberRef invokedMethod;
    private String str;

    public ReduceByKeyEvent(int iid, MemberRef containingMethod, int lineNumber) {
        super(iid, containingMethod, lineNumber);
        this.invokedMethod = containingMethod;
    }

    public String getInvokedMethodName() {
        if (str == null) {
            this.str = invokedMethod.getOwner() + "#" + invokedMethod.getName();
//                    + invokedMethod.getDesc();
        }
        return str;
    }

    @Override
    public String toString() {
        return String.format("REDUCEBYKEY(%d,%d,%s)", iid, lineNumber, getInvokedMethodName());
    }

    @Override
    public void applyVisitor(TraceEventVisitor v) {
//        throw new UnsupportedOperationException("No visitor method for custom event");
        v.visitReduceByKeyEvent(this);
    }
}