CREATE TRIGGER check_quantity
    ON order_detail
    instead of INSERT
    AS
BEGIN
    declare @quantity int;
    select @quantity = quantity from commodity where commodity.id = (select commodity_id from inserted);
    declare @order_quantity int;
    select @order_quantity = order_quantity from inserted;
    declare @id bigint;
    select @id = id from inserted;
    declare @order_detail_no varchar(255);
    select @order_detail_no = order_detail_no from inserted;
    declare @total_amount numeric(8, 2);
    select @total_amount = total_amount from inserted;
    declare @commodity_id bigint;
    select @commodity_id = commodity_id from inserted;
    declare @order_master_id bigint;
    select @order_master_id = order_master_id from inserted;
    begin transaction od_insert
        if @quantity < @order_quantity
            begin
                rollback transaction
            end
        insert into order_detail(id, order_detail_no, order_quantity, total_amount, commodity_id, order_master_id)
        values (@id, @order_detail_no, @order_quantity, @total_amount, @commodity_id, @order_master_id);
        if @@error != 0
            rollback transaction
        update commodity
        set quantity = quantity - @order_quantity
        where id = (select commodity_id from inserted);
        if @@error != 0
            rollback transaction
    commit transaction
END
go


