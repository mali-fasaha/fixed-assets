export interface IServiceOutlet {
  id?: number;
  serviceOutletCode?: string;
  serviceOutletDesignation?: string;
  description?: string;
  location?: string;
}

export class ServiceOutlet implements IServiceOutlet {
  constructor(
    public id?: number,
    public serviceOutletCode?: string,
    public serviceOutletDesignation?: string,
    public description?: string,
    public location?: string
  ) {}
}
