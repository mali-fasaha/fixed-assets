import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IFixedAssetInvoice } from 'app/shared/model/fixed-asset-invoice.model';

type EntityResponseType = HttpResponse<IFixedAssetInvoice>;
type EntityArrayResponseType = HttpResponse<IFixedAssetInvoice[]>;

@Injectable({ providedIn: 'root' })
export class FixedAssetInvoiceService {
  public resourceUrl = SERVER_API_URL + 'api/fixed-asset-invoices';
  public resourceSearchUrl = SERVER_API_URL + 'api/_search/fixed-asset-invoices';

  constructor(protected http: HttpClient) {}

  create(fixedAssetInvoice: IFixedAssetInvoice): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(fixedAssetInvoice);
    return this.http
      .post<IFixedAssetInvoice>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(fixedAssetInvoice: IFixedAssetInvoice): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(fixedAssetInvoice);
    return this.http
      .put<IFixedAssetInvoice>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IFixedAssetInvoice>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IFixedAssetInvoice[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  search(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IFixedAssetInvoice[]>(this.resourceSearchUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  protected convertDateFromClient(fixedAssetInvoice: IFixedAssetInvoice): IFixedAssetInvoice {
    const copy: IFixedAssetInvoice = Object.assign({}, fixedAssetInvoice, {
      invoiceDate:
        fixedAssetInvoice.invoiceDate != null && fixedAssetInvoice.invoiceDate.isValid()
          ? fixedAssetInvoice.invoiceDate.format(DATE_FORMAT)
          : null
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.invoiceDate = res.body.invoiceDate != null ? moment(res.body.invoiceDate) : null;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((fixedAssetInvoice: IFixedAssetInvoice) => {
        fixedAssetInvoice.invoiceDate = fixedAssetInvoice.invoiceDate != null ? moment(fixedAssetInvoice.invoiceDate) : null;
      });
    }
    return res;
  }
}
