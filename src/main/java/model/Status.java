
package model;

public enum Status {
    ACTIVE(1),
    DELETED(2);
    private final long id;

    Status(long id) {
        this.id = id;
    }

    public long getId() {
        return this.id;
    }

    public static Status getStatusById(Long id) {
        for (Status status : Status.values()) {
            if (status.getId() == id) {
                return status;
            }
        }
        throw new RuntimeException("There is no such status in enum list.");
    }
}


