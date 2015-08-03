package cn.flus.account.web.filter;

public class SigninContextHolder {

    private static final ThreadLocal<SigninContext> holder = new ThreadLocal<SigninContext>();

    public static void clearContext() {
        holder.remove();
    }

    public static SigninContext getContext() {
        SigninContext ctx = holder.get();
        if (ctx == null) {
            ctx = createEmptyContext();
            holder.set(ctx);
        }
        return ctx;
    }

    public static void setContext(SigninContext loginContext) {
        holder.set(loginContext);
    }

    public static SigninContext createEmptyContext() {
        return new SigninContext();
    }

}
