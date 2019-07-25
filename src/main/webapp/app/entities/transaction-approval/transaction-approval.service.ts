import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { ITransactionApproval } from 'app/shared/model/transaction-approval.model';

type EntityResponseType = HttpResponse<ITransactionApproval>;
type EntityArrayResponseType = HttpResponse<ITransactionApproval[]>;

@Injectable({ providedIn: 'root' })
export class TransactionApprovalService {
  public resourceUrl = SERVER_API_URL + 'api/transaction-approvals';
  public resourceSearchUrl = SERVER_API_URL + 'api/_search/transaction-approvals';

  constructor(protected http: HttpClient) {}

  create(transactionApproval: ITransactionApproval): Observable<EntityResponseType> {
    return this.http.post<ITransactionApproval>(this.resourceUrl, transactionApproval, { observe: 'response' });
  }

  update(transactionApproval: ITransactionApproval): Observable<EntityResponseType> {
    return this.http.put<ITransactionApproval>(this.resourceUrl, transactionApproval, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<ITransactionApproval>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ITransactionApproval[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  search(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ITransactionApproval[]>(this.resourceSearchUrl, { params: options, observe: 'response' });
  }
}
