import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IServiceOutlet } from 'app/shared/model/service-outlet.model';

type EntityResponseType = HttpResponse<IServiceOutlet>;
type EntityArrayResponseType = HttpResponse<IServiceOutlet[]>;

@Injectable({ providedIn: 'root' })
export class ServiceOutletService {
  public resourceUrl = SERVER_API_URL + 'api/service-outlets';
  public resourceSearchUrl = SERVER_API_URL + 'api/_search/service-outlets';

  constructor(protected http: HttpClient) {}

  create(serviceOutlet: IServiceOutlet): Observable<EntityResponseType> {
    return this.http.post<IServiceOutlet>(this.resourceUrl, serviceOutlet, { observe: 'response' });
  }

  update(serviceOutlet: IServiceOutlet): Observable<EntityResponseType> {
    return this.http.put<IServiceOutlet>(this.resourceUrl, serviceOutlet, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IServiceOutlet>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IServiceOutlet[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  search(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IServiceOutlet[]>(this.resourceSearchUrl, { params: options, observe: 'response' });
  }
}
