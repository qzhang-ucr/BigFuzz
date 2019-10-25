package janala.logger.inst;

public class DATAFLOW extends Instruction  {
    String type;

    public DATAFLOW(int iid, int mid, String type) {
        super(iid, mid);
        this.type = type;
    }

    public void visit(IVisitor visitor) {
        visitor.visitDATAFLOW(this);
    }

    @Override
    public String toString() {
        return "DATAFLOW iid=" + iid + " mid=" + mid;
    }
}
