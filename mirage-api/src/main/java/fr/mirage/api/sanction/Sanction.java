package fr.mirage.api.sanction;

public interface Sanction {

    String getSanctionId();

    SanctionType getType();

    String getReason();

    long emitAt();
    long getDuration();

    boolean isActive();

}