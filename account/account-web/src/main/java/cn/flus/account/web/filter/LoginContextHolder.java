package cn.flus.account.web.filter;


public class LoginContextHolder {

    private static final ThreadLocal<LoginContext> holder = new ThreadLocal<LoginContext>();

    public static void clearContext() {
        holder.remove();
    }

    public static LoginContext getContext() {
        LoginContext ctx = holder.get();
        if (ctx == null) {
            ctx = createEmptyContext();
            holder.set(ctx);
        }
        return ctx;
    }

    public static void setContext(LoginContext loginContext) {
        holder.set(loginContext);
    }

    public static LoginContext createEmptyContext() {
        return new LoginContext();
    }

}
