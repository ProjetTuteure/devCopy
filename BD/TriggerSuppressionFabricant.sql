CREATE TRIGGER [dbo].[supprimerFabricant] on [dbo].[FABRICANT]
INSTEAD OF DELETE
AS BEGIN
	declare @idFabricant int;
	DECLARE CursorFabricant CURSOR FOR SELECT idFabricant FROM DELETED
	OPEN CursorFabricant
	FETCH CursorFabricant INTO @idFabricant
	WHILE @@FETCH_STATUS=0
	BEGIN
		DELETE MATERIEL WHERE idFabricant=@idFabricant
		DELETE COMPOSANT WHERE idFabricant=@idFabricant
		DELETE FABRICANT WHERE idFabricant=@idFabricant
		FETCH CursorFabricant INTO @idFabricant
	END
	CLOSE CursorFabricant
	DEALLOCATE CursorFabricant
END
