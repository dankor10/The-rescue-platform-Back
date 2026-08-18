package by.lectoria.userservice.model;

public class Views {
    public interface UserView {
        public interface Get {}
        public interface Post {}
        public interface Put {}
        public interface Profile {}
    }

    public interface RefreshTokenView {
        public interface Get {}
        public interface Post {}
        public interface Put {}
    }

    public interface UserWithRefreshTokenView extends UserView.Get, RefreshTokenView.Get {}
}