import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IAssetTransaction } from 'app/shared/model/asset-transaction.model';

type EntityResponseType = HttpResponse<IAssetTransaction>;
type EntityArrayResponseType = HttpResponse<IAssetTransaction[]>;

@Injectable({ providedIn: 'root' })
export class AssetTransactionService {
  public resourceUrl = SERVER_API_URL + 'api/asset-transactions';
  public resourceSearchUrl = SERVER_API_URL + 'api/_search/asset-transactions';

  constructor(protected http: HttpClient) {}

  create(assetTransaction: IAssetTransaction): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(assetTransaction);
    return this.http
      .post<IAssetTransaction>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(assetTransaction: IAssetTransaction): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(assetTransaction);
    return this.http
      .put<IAssetTransaction>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IAssetTransaction>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IAssetTransaction[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  search(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IAssetTransaction[]>(this.resourceSearchUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  protected convertDateFromClient(assetTransaction: IAssetTransaction): IAssetTransaction {
    const copy: IAssetTransaction = Object.assign({}, assetTransaction, {
      transactionDate:
        assetTransaction.transactionDate != null && assetTransaction.transactionDate.isValid()
          ? assetTransaction.transactionDate.format(DATE_FORMAT)
          : null
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.transactionDate = res.body.transactionDate != null ? moment(res.body.transactionDate) : null;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((assetTransaction: IAssetTransaction) => {
        assetTransaction.transactionDate = assetTransaction.transactionDate != null ? moment(assetTransaction.transactionDate) : null;
      });
    }
    return res;
  }
}
