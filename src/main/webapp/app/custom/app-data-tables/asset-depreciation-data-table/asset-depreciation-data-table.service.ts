import { Injectable } from '@angular/core';
import { map } from 'rxjs/operators';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { SERVER_API_URL } from 'app/app.constants';
import { Observable } from 'rxjs/index';
import { createRequestOption } from 'app/shared';
import { IAssetDepreciation } from 'app/shared/model/asset-depreciation.model';
import moment = require('moment');

type EntityResponseType = HttpResponse<IAssetDepreciation>;
type EntityArrayResponseType = HttpResponse<IAssetDepreciation[]>;

@Injectable({
  providedIn: 'root'
})
export class AssetDepreciationDataTableService {
  public resourceUrl = SERVER_API_URL + 'api/asset-depreciations';
  public resourceSearchUrl = SERVER_API_URL + 'api/_search/asset-depreciations';

  constructor(protected http: HttpClient) {}

  create(assetDepreciation: IAssetDepreciation): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(assetDepreciation);
    return this.http
      .post<IAssetDepreciation>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(assetDepreciation: IAssetDepreciation): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(assetDepreciation);
    return this.http
      .put<IAssetDepreciation>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IAssetDepreciation>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IAssetDepreciation[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  search(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IAssetDepreciation[]>(this.resourceSearchUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  protected convertDateFromClient(assetDepreciation: IAssetDepreciation): IAssetDepreciation {
    const copy: IAssetDepreciation = Object.assign({}, assetDepreciation, {
      depreciationDate:
        assetDepreciation.depreciationDate != null && assetDepreciation.depreciationDate.isValid()
          ? assetDepreciation.depreciationDate.format(DATE_FORMAT)
          : null
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.depreciationDate = res.body.depreciationDate != null ? moment(res.body.depreciationDate) : null;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((assetDepreciation: IAssetDepreciation) => {
        assetDepreciation.depreciationDate = assetDepreciation.depreciationDate != null ? moment(assetDepreciation.depreciationDate) : null;
      });
    }
    return res;
  }
}
