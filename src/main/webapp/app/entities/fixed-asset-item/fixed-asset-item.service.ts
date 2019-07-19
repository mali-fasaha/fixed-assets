import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IFixedAssetItem } from 'app/shared/model/fixed-asset-item.model';

type EntityResponseType = HttpResponse<IFixedAssetItem>;
type EntityArrayResponseType = HttpResponse<IFixedAssetItem[]>;

@Injectable({ providedIn: 'root' })
export class FixedAssetItemService {
  public resourceUrl = SERVER_API_URL + 'api/fixed-asset-items';
  public resourceSearchUrl = SERVER_API_URL + 'api/_search/fixed-asset-items';

  constructor(protected http: HttpClient) {}

  create(fixedAssetItem: IFixedAssetItem): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(fixedAssetItem);
    return this.http
      .post<IFixedAssetItem>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(fixedAssetItem: IFixedAssetItem): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(fixedAssetItem);
    return this.http
      .put<IFixedAssetItem>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IFixedAssetItem>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IFixedAssetItem[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  search(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IFixedAssetItem[]>(this.resourceSearchUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  protected convertDateFromClient(fixedAssetItem: IFixedAssetItem): IFixedAssetItem {
    const copy: IFixedAssetItem = Object.assign({}, fixedAssetItem, {
      purchaseDate:
        fixedAssetItem.purchaseDate != null && fixedAssetItem.purchaseDate.isValid()
          ? fixedAssetItem.purchaseDate.format(DATE_FORMAT)
          : null
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.purchaseDate = res.body.purchaseDate != null ? moment(res.body.purchaseDate) : null;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((fixedAssetItem: IFixedAssetItem) => {
        fixedAssetItem.purchaseDate = fixedAssetItem.purchaseDate != null ? moment(fixedAssetItem.purchaseDate) : null;
      });
    }
    return res;
  }
}
