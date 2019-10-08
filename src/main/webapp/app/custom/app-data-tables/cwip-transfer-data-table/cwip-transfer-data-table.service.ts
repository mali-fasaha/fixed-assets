import { map } from 'rxjs/operators';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { SERVER_API_URL } from 'app/app.constants';
import { ICwipTransfer } from 'app/shared/model/cwip-transfer.model';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs/index';
import { createRequestOption } from 'app/shared';
import moment = require('moment');

type EntityResponseType = HttpResponse<ICwipTransfer>;
type EntityArrayResponseType = HttpResponse<ICwipTransfer[]>;

@Injectable({ providedIn: 'root' })
export class CwipTransferDataTableService {
  public resourceUrl = SERVER_API_URL + 'api/cwip-transfers';
  public resourceSearchUrl = SERVER_API_URL + 'api/_search/cwip-transfers';

  constructor(protected http: HttpClient) {}

  create(cwipTransfer: ICwipTransfer): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(cwipTransfer);
    return this.http
      .post<ICwipTransfer>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(cwipTransfer: ICwipTransfer): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(cwipTransfer);
    return this.http
      .put<ICwipTransfer>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<ICwipTransfer>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<ICwipTransfer[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  search(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<ICwipTransfer[]>(this.resourceSearchUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  protected convertDateFromClient(cwipTransfer: ICwipTransfer): ICwipTransfer {
    const copy: ICwipTransfer = Object.assign({}, cwipTransfer, {
      transferMonth:
        cwipTransfer.transferMonth != null && cwipTransfer.transferMonth.isValid() ? cwipTransfer.transferMonth.format(DATE_FORMAT) : null
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.transferMonth = res.body.transferMonth != null ? moment(res.body.transferMonth) : null;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((cwipTransfer: ICwipTransfer) => {
        cwipTransfer.transferMonth = cwipTransfer.transferMonth != null ? moment(cwipTransfer.transferMonth) : null;
      });
    }
    return res;
  }
}
