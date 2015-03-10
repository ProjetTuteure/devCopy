CREATE TRIGGER suppressionLogiciel ON LOGICIEL INSTEAD OF DELETE AS
BEGIN
	DECLARE @idLogicielASupprimer INT
	DECLARE CursorLogiciel CURSOR FOR SELECT idLogiciel FROM DELETED
	OPEN CursorLogiciel
	FETCH CursorLogiciel INTO @idLogicielASupprimer
	WHILE @@FETCH_STATUS=0
	BEGIN
		DELETE FROM ESTINSTALLE WHERE idLogiciel=@idLogicielASupprimer
		DELETE FROM LOGICIEL WHERE idLogiciel=@idLogicielASupprimer
		FETCH CursorLogiciel INTO @idLogicielASupprimer
	END
	CLOSE CursorLogiciel
	DEALLOCATE CursorLogiciel
END