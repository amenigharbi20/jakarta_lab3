# Lab 3 - EJB Service Layer: Chapter 3 Activities

## Activity 1 - PromotionService
PromotionService is a separate @Stateless bean that depends on CartService. CartService only knows which items are in the cart, while pricing rules (a flat 10% discount) are business policy that changes often. Keeping them apart respects the single responsibility principle.

## Activity 2 - NotificationService history
NotificationService is a @Singleton, so there is one instance for the whole application. Its in-memory list of the last 5 confirmations (name + timestamp) therefore survives between different users' checkouts. @Lock(WRITE) and @Lock(READ) make access to the shared list thread-safe.

## Activity 3 - Empty cart guard
OrderService.createOrder throws EmptyCartException (annotated @ApplicationException(rollback = true)) when the cart is empty. The check runs before the notification, so no [NOTIFY] line is logged and the transaction is rolled back. CheckoutServlet catches the exception and shows an error message on the checkout page.

## Activity 4 - Injection chain (breakpoint in OrderService.createOrder)

1. Browser and Undertow: The browser sends POST /checkout with the name, email and address form fields. Undertow, the web server inside WildFly, receives the request. It selects the servlet mapped to /checkout and starts the filter chain.
2. Filters: RequestLoggingFilter and AuthenticationFilter run before the servlet. They can log the request, measure its duration or block it. They then call chain.doFilter to pass the request on to the servlet.
3. CheckoutServlet.doPost: The servlet reads the three form parameters from the request. It contains no business logic. It only calls orderService.createOrder(...) on the injected OrderService.
4. EJB container (proxy and interceptors): The injected orderService is a proxy created by the container, not the real object. The container uses it to pick a bean instance from the pool and to manage the transaction. It also resolves the injected CartService and NotificationService before the call reaches the bean.
5. OrderService.createOrder: This is where execution stops at the breakpoint, inside the transactional boundary. The method checks the cart through CartService. It then calls NotificationService to log the confirmation and clears the cart.

## Activity 5 - Reflection
In Lab 2, CheckoutServlet did everything itself: it read the form, accessed HttpSession, handled the cart and ran the order logic. In Lab 3, it only translates HTTP form fields into a call to orderService.createOrder(...). The business logic moved into OrderService, the cart into CartService and the notification into NotificationService, and the container injects them. A user sees no difference, but the code is easier to test, reuse (a REST API in Lab 5 can call the same services) and change, because each class has one responsibility.