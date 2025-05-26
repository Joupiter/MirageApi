package fr.mirage.api.sanction;

public class SanctionId {

    public static String newId(SanctionType type, long emitAt, long count) {
        return type + "-" + emitAt + "-" + count;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    static class Builder {

        private final String id;
        private final String[] idChar;

        public Builder() {
            this.id = "a-b-c";
            this.idChar = id.split("-");
        }

        public Builder setType(SanctionType type) {
            this.idChar[0] = type.name();
            return this;
        }

        public Builder setEmitAt(long emitAt) {
            this.idChar[1] = String.valueOf(emitAt);
            return this;
        }

        public Builder setCount(long count) {
            this.idChar[2] = String.valueOf(count);
            return this;
        }

        public String build() {
            return id;
        }

    }

}