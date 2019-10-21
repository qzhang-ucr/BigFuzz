package edu.ucla.cs.bigfuzz.dataflow;

import edu.berkeley.cs.jqf.instrument.tracing.events.TraceEvent;
import edu.berkeley.cs.jqf.instrument.tracing.events.TraceEventVisitor;
import janala.logger.inst.MemberRef;

public class MapToPairEvent extends TraceEvent {

    protected final MemberRef invokedMethod;
    private String str;

//    public MapToPairEvent(int iid, MemberRef containingMethod, int lineNumber, MemberRef invokedMethod) {
//        super(iid, containingMethod, lineNumber);
//        this.invokedMethod = invokedMethod;
//    }

    public MapToPairEvent(int iid, MemberRef containingMethod, int lineNumber) {
        super(iid, containingMethod, lineNumber);
        this.invokedMethod = containingMethod;
    }

    public String getInvokedMethodName() {
        if (str == null) {
            this.str = containingMethod.getOwner() + "#" + containingMethod.getName();
//                    + invokedMethod.getDesc();
        }
        return str;
    }

    @Override
    public String toString() {
        return String.format("MAPTOPAIR(%d,%d, %s)", iid, lineNumber, getInvokedMethodName());
    }

    @Override
    public void applyVisitor(TraceEventVisitor v) {
//        throw new UnsupportedOperationException("No visitor method for custom event");
        v.visitMapToPairEvent(this);
    }
}
