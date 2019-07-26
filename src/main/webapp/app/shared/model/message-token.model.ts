export interface IMessageToken {
  id?: number;
  description?: string;
  timeSent?: number;
  tokenValue?: string;
  received?: boolean;
}

export class MessageToken implements IMessageToken {
  constructor(
    public id?: number,
    public description?: string,
    public timeSent?: number,
    public tokenValue?: string,
    public received?: boolean
  ) {
    this.received = this.received || false;
  }
}
