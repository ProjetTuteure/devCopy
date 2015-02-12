CREATE TRIGGER SuppressionRevendeur on REVENDEUR
INSTEAD OF DELETE
AS BEGIN 
	declare @idRevendeur int
	DECLARE CursorRevendeur CURSOR FOR SELECT idRevendeur FROM DELETED
	OPEN CursorRevendeur
	FETCH CursorRevendeur INTO @idRevendeur
	WHILE @@FETCH_STATUS=0
	BEGIN
		DELETE FROM FACTURE WHERE idRevendeur=@idRevendeur
		DELETE FROM REVENDEUR WHERE idRevendeur=@idRevendeur
		FETCH CursorRevendeur INTO @idRevendeur
	END
	CLOSE CursorRevendeur
	DEALLOCATE CursorRevendeur
END