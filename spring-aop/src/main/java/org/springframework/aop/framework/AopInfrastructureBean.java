package org.springframework.aop.framework;

/**
 * Marker interface that indicates a bean that is part of Spring's AOP infrastructure. In
 * particular, this implies that any such bean is not subject to auto-proxying, even if a pointcut
 * would match.
 *
 * @author Juergen Hoeller
 * @see org.springframework.aop.framework.autoproxy.AbstractAutoProxyCreator
 * @see org.springframework.aop.scope.ScopedProxyFactoryBean
 * @since 2.0.3
 */
public interface AopInfrastructureBean {

}
