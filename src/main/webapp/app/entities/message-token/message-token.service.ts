import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IMessageToken } from 'app/shared/model/message-token.model';

type EntityResponseType = HttpResponse<IMessageToken>;
type EntityArrayResponseType = HttpResponse<IMessageToken[]>;

@Injectable({ providedIn: 'root' })
export class MessageTokenService {
  public resourceUrl = SERVER_API_URL + 'api/message-tokens';
  public resourceSearchUrl = SERVER_API_URL + 'api/_search/message-tokens';

  constructor(protected http: HttpClient) {}

  create(messageToken: IMessageToken): Observable<EntityResponseType> {
    return this.http.post<IMessageToken>(this.resourceUrl, messageToken, { observe: 'response' });
  }

  update(messageToken: IMessageToken): Observable<EntityResponseType> {
    return this.http.put<IMessageToken>(this.resourceUrl, messageToken, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IMessageToken>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IMessageToken[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  search(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IMessageToken[]>(this.resourceSearchUrl, { params: options, observe: 'response' });
  }
}
