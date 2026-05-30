public enum LoanStatus {
    REQUESTED,
    BORROWED,
    RETURNED,
    OVERDUE,
    REJECTED;
    public boolean moveTo(LoanStatus next){
        switch(this){
            case REQUESTED:
                return next == BORROWED || next == REJECTED;
            case BORROWED:
                return next == RETURNED || next == OVERDUE;
                case RETURNED:
                    return false;
                default:
                    return false;
        }         
    }
}
