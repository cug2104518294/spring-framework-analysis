package org.springframework.core;

/**
 * 定义对 alias 的简单增删改等操作。 Common interface for managing aliases. Serves as super-interface for {@link
 * org.springframework.beans.factory.support.BeanDefinitionRegistry}.
 */
public interface AliasRegistry {

	/**
	 * Given a name, register an alias for it.
	 *
	 * @param name  the canonical name
	 * @param alias the alias to be registered
	 * @throws IllegalStateException if the alias is already in use and may not be overridden
	 */
	void registerAlias(String name, String alias);

	/**
	 * Remove the specified alias from this registry.
	 *
	 * @param alias the alias to remove
	 * @throws IllegalStateException if no such alias was found
	 */
	void removeAlias(String alias);

	/**
	 * Determine whether this given name is defines as an alias (as opposed to the name of an
	 * actually registered component).
	 *
	 * @param name the name to check
	 * @return whether the given name is an alias
	 */
	boolean isAlias(String name);

	/**
	 * Return the aliases for the given name, if defined.
	 *
	 * @param name the name to check for aliases
	 * @return the aliases, or an empty array if none
	 */
	String[] getAliases(String name);

}
