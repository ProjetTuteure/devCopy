USE [ProjetTutore]
GO
/****** Object:  StoredProcedure [dbo].[updateCheminDriverMateriel]    Script Date: 20/03/2015 16:10:42 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE PROCEDURE [dbo].[updateCheminDriverMateriel] @repertoire VARCHAR(500)
AS
DECLARE CursorMateriel CURSOR FOR
SELECT idMateriel from materiel
DECLARE @idMateriel INT
OPEN CursorMateriel
FETCH CursorMateriel INTO @idMateriel
WHILE @@FETCH_STATUS=0
BEGIN
	UPDATE MATERIEL SET repertoireDrivers=@repertoire+'/'+CONVERT(VARCHAR,@idMateriel) WHERE idMateriel=@idMateriel
	FETCH CursorMateriel INTO @idMateriel
END
CLOSE CursorMateriel
DEALLOCATE CursorMateriel