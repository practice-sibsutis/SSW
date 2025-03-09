CREATE TABLE IF NOT EXISTS public.customers
(
    customer_id integer GENERATED ALWAYS AS IDENTITY NOT NULL,
    email text NOT NULL,
    phone text NOT NULL,
    name text NOT NULL,
    delivery_address text,
    CONSTRAINT "Customers_pkey" PRIMARY KEY (customer_id)
);

CREATE TABLE IF NOT EXISTS public.products
(
    product_id integer GENERATED ALWAYS AS IDENTITY NOT NULL,
    name text NOT NULL,
    cost integer NOT NULL,
    description text,
    CONSTRAINT "Items_pkey" PRIMARY KEY (product_id),
    CONSTRAINT "cost greater or equal then zero" CHECK (cost >= 0) NOT VALID
);

CREATE TABLE IF NOT EXISTS public.orders
(
    order_id integer GENERATED ALWAYS AS IDENTITY NOT NULL,
    customer_id integer NOT NULL,
    ordering_time timestamp with time zone,
    title text NOT NULL,
    CONSTRAINT "OrderPK" PRIMARY KEY (order_id),
    CONSTRAINT "Customer" FOREIGN KEY (customer_id)
        REFERENCES public.customers (customer_id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
);

CREATE TABLE IF NOT EXISTS public.detailed_orders
(
    order_id integer NOT NULL,
    product_id integer NOT NULL,
    quantity integer NOT NULL,
    amount integer NOT NULL,
    CONSTRAINT "orders_pkey" PRIMARY KEY (order_id, product_id),
    CONSTRAINT "Product" FOREIGN KEY (product_id)
        REFERENCES public.products (product_id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION,
    CONSTRAINT "OrderFK" FOREIGN KEY (order_id)
        REFERENCES public.orders (order_id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION,
    CONSTRAINT "amount greater or equal then zero" CHECK (amount >= 0),
    CONSTRAINT "quantity greater then zero" CHECK (quantity > 0)
);