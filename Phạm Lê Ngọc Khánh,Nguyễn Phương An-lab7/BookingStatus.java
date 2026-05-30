public enum BookingStatus{
    PENDING,
    APPROVED,
    REJECTED,
    CANCELLED,
    COMPLETED;
    public boolean changeTo(BookingStatus next){
        switch(this){
            case PENDING:
                return next == APPROVED || next == REJECTED || next == CANCELLED;
            case APPROVED:
                return next == COMPLETED || next == CANCELLED;
                case REJECTED:
                case CANCELLED:  
                case COMPLETED:
                    return false;
                default:
                    return false;
        }
    }
}
