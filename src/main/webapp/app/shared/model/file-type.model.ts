export const enum FileMediumTypes {
  EXCEL = 'EXCEL',
  PDF = 'PDF',
  POWERPOINT = 'POWERPOINT',
  DOC = 'DOC',
  TEXT = 'TEXT',
  JSON = 'JSON',
  HTML5 = 'HTML5'
}

export interface IFileType {
  id?: number;
  fileTypeName?: string;
  fileMediumType?: FileMediumTypes;
  description?: string;
}

export class FileType implements IFileType {
  constructor(public id?: number, public fileTypeName?: string, public fileMediumType?: FileMediumTypes, public description?: string) {}
}
