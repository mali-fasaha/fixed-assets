import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IAssetAcquisition } from 'app/shared/model/asset-acquisition.model';

type EntityResponseType = HttpResponse<IAssetAcquisition>;
type EntityArrayResponseType = HttpResponse<IAssetAcquisition[]>;

@Injectable({ providedIn: 'root' })
export class AssetAcquisitionService {
  public resourceUrl = SERVER_API_URL + 'api/asset-acquisitions';
  public resourceSearchUrl = SERVER_API_URL + 'api/_search/asset-acquisitions';

  constructor(protected http: HttpClient) {}

  create(assetAcquisition: IAssetAcquisition): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(assetAcquisition);
    return this.http
      .post<IAssetAcquisition>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(assetAcquisition: IAssetAcquisition): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(assetAcquisition);
    return this.http
      .put<IAssetAcquisition>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IAssetAcquisition>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IAssetAcquisition[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  search(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IAssetAcquisition[]>(this.resourceSearchUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  protected convertDateFromClient(assetAcquisition: IAssetAcquisition): IAssetAcquisition {
    const copy: IAssetAcquisition = Object.assign({}, assetAcquisition, {
      acquisitionMonth:
        assetAcquisition.acquisitionMonth != null && assetAcquisition.acquisitionMonth.isValid()
          ? assetAcquisition.acquisitionMonth.format(DATE_FORMAT)
          : null
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.acquisitionMonth = res.body.acquisitionMonth != null ? moment(res.body.acquisitionMonth) : null;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((assetAcquisition: IAssetAcquisition) => {
        assetAcquisition.acquisitionMonth = assetAcquisition.acquisitionMonth != null ? moment(assetAcquisition.acquisitionMonth) : null;
      });
    }
    return res;
  }
}
