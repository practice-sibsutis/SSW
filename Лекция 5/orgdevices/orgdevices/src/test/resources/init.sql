CREATE TABLE IF NOT EXISTS public.shops
(
    shop_id integer NOT NULL,
    address text NOT NULL,
    directors_name text NOT NULL,
    phone text NOT NULL,
    working_time text NOT NULL,
    CONSTRAINT "Shops_pkey" PRIMARY KEY (shop_id)
);

CREATE TABLE IF NOT EXISTS public.customers
(
    customer_id integer NOT NULL,
    email text NOT NULL,
    phone text NOT NULL,
    name text NOT NULL,
    delivery_address text,
    CONSTRAINT "Customers_pkey" PRIMARY KEY (customer_id)
);

CREATE TABLE IF NOT EXISTS public.products
(
    product_id integer NOT NULL,
    name text NOT NULL,
    cost integer NOT NULL,
    description text,
    CONSTRAINT "Items_pkey" PRIMARY KEY (product_id),
    CONSTRAINT "cost greater or equal then zero" CHECK (cost >= 0) NOT VALID
);

CREATE TABLE IF NOT EXISTS public.orders
(
    order_id integer NOT NULL,
    product_id integer NOT NULL,
    customer_id integer NOT NULL,
    quantity integer NOT NULL,
    amount integer NOT NULL,
    shop_id integer NOT NULL,
    ordering_time timestamp with time zone,
    CONSTRAINT "orders_pkey" PRIMARY KEY (order_id, product_id, customer_id),
    CONSTRAINT "Processed shop" FOREIGN KEY (shop_id)
        REFERENCES public.shops (shop_id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION,
    CONSTRAINT "Customer" FOREIGN KEY (customer_id)
        REFERENCES public.customers (customer_id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION,
    CONSTRAINT "Product" FOREIGN KEY (product_id)
        REFERENCES public.products (product_id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION,
    CONSTRAINT "amount greater or equal then zero" CHECK (amount >= 0),
    CONSTRAINT "quantity greater then zero" CHECK (quantity > 0)
);

-- Добавил поле "order_id", чтобы сделать ограничение, что покупатель может оставлять отзыв на товар
-- который покупал
CREATE TABLE IF NOT EXISTS public.Feedbacks
(
    order_id integer NOT NULL,
	product_id integer NOT NULL,
    customer_id integer NOT NULL,
    datetime timestamp with time zone NOT NULL,
    raiting smallint,
    feedback_text text NOT NULL,
    CONSTRAINT "FeedBack_pkey" PRIMARY KEY (customer_id, product_id),
    CONSTRAINT "Feedback-order" FOREIGN KEY (order_id, customer_id, product_id)
        REFERENCES public.orders (order_id, customer_id, product_id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION,
    CONSTRAINT "raiting1-5" CHECK (raiting >= 1 AND raiting <= 5)
);