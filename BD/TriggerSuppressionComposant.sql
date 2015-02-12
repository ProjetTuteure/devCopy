CREATE TRIGGER supprimerComposant on COMPOSANT
INSTEAD OF DELETE
AS BEGIN
	declare @idComposant int;
	DECLARE CursorComposant CURSOR FOR SELECT idComposant FROM DELETED
	OPEN CursorComposant
	FETCH CursorComposant INTO @idComposant
	WHILE @@FETCH_STATUS=0
	BEGIN
		DELETE FROM COMPOSE WHERE idComposant=@idComposant
		DELETE FROM COMPOSANT WHERE idComposant=@idComposant
		FETCH CursorComposant INTO @idComposant
	END
	CLOSE CursorComposant
	DEALLOCATE CursorComposant
END
