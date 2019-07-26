package io.github.assets.app.messaging;

import io.github.assets.domain.MessageToken;

/**
 * This is an abstraction for sending a services into a queue
 *
 * @param <T> Type of services
 */
public interface MessageService<T> {

    /**
     * This method sends a services of type T into a queue destination and returns a token id.
     *
     * @param message This is the item being sent
     * @return This is the token for the message that has just been sent
     */
    MessageToken sendMessage(final T message);
}
