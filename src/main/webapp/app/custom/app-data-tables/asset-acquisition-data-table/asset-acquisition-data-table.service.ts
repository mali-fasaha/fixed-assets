import { Injectable } from '@angular/core';
import { SERVER_API_URL } from 'app/app.constants';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { map } from 'rxjs/operators';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { createRequestOption } from 'app/shared';
import { IAssetAcquisition } from 'app/shared/model/asset-acquisition.model';
import { Observable } from 'rxjs/index';
import * as moment from 'moment';

type EntityResponseType = HttpResponse<IAssetAcquisition>;
type EntityArrayResponseType = HttpResponse<IAssetAcquisition[]>;

@Injectable({
  providedIn: 'root'
})
export class AssetAcquisitionDataTableService {
  // TODO Use non-paging api
  // public resourceUrl = SERVER_API_URL + 'api/asset-acquisitions';
  public resourceUrl = 'http://5d9afe02686ed000144d1ab6.mockapi.io/api/assets-acquisition';

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

  fetchAllAssetAcquisitions(): Observable<EntityArrayResponseType> {
    return this.http
      .get<IAssetAcquisition[]>(this.resourceUrl, { observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
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
