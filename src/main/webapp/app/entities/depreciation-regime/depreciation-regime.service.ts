import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IDepreciationRegime } from 'app/shared/model/depreciation-regime.model';

type EntityResponseType = HttpResponse<IDepreciationRegime>;
type EntityArrayResponseType = HttpResponse<IDepreciationRegime[]>;

@Injectable({ providedIn: 'root' })
export class DepreciationRegimeService {
  public resourceUrl = SERVER_API_URL + 'api/depreciation-regimes';
  public resourceSearchUrl = SERVER_API_URL + 'api/_search/depreciation-regimes';

  constructor(protected http: HttpClient) {}

  create(depreciationRegime: IDepreciationRegime): Observable<EntityResponseType> {
    return this.http.post<IDepreciationRegime>(this.resourceUrl, depreciationRegime, { observe: 'response' });
  }

  update(depreciationRegime: IDepreciationRegime): Observable<EntityResponseType> {
    return this.http.put<IDepreciationRegime>(this.resourceUrl, depreciationRegime, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IDepreciationRegime>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IDepreciationRegime[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  search(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IDepreciationRegime[]>(this.resourceSearchUrl, { params: options, observe: 'response' });
  }
}
