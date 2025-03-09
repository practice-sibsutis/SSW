-- 1. Выборка товаров с максимальным количеством заказов (самый продаваемый товар)
SELECT orders.product_id, products.name, SUM(orders.quantity) AS quantity
FROM products
LEFT JOIN orders
ON products.product_id = orders.product_id
GROUP BY orders.product_id, products.name
ORDER BY quantity DESC;

-- 2. Выборка магазинов которые обрабатывают наибольшее количество заказов
SELECT orders.shop_id, shops.address, COUNT(orders.shop_id) as count_orders
FROM shops
LEFT JOIN orders
ON shops.shop_id = orders.shop_id
GROUP BY orders.shop_id, shops.address
ORDER BY count_orders DESC;

-- 3. Выборка товара с максимальным количеством отзывов
SELECT feedbacks.product_id, products.name, COUNT(feedbacks.product_id) AS count_reviews
FROM products
LEFT JOIN feedbacks
ON products.product_id = feedbacks.product_id
GROUP BY feedbacks.product_id, products.name
ORDER BY count_reviews DESC
LIMIT 1;

-- 4. Выборка товара с минимальным рейтингом
-- Я брал минимальный средний рейтинг
SELECT feedbacks.product_id, products.name, COALESCE(AVG(feedbacks.raiting), 0) AS average_rating
FROM products
LEFT JOIN feedbacks
ON products.product_id = feedbacks.product_id
GROUP BY feedbacks.product_id, products.name
ORDER BY average_rating
LIMIT 1;