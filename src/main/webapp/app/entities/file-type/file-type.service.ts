import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IFileType } from 'app/shared/model/file-type.model';

type EntityResponseType = HttpResponse<IFileType>;
type EntityArrayResponseType = HttpResponse<IFileType[]>;

@Injectable({ providedIn: 'root' })
export class FileTypeService {
  public resourceUrl = SERVER_API_URL + 'api/file-types';
  public resourceSearchUrl = SERVER_API_URL + 'api/_search/file-types';

  constructor(protected http: HttpClient) {}

  create(fileType: IFileType): Observable<EntityResponseType> {
    return this.http.post<IFileType>(this.resourceUrl, fileType, { observe: 'response' });
  }

  update(fileType: IFileType): Observable<EntityResponseType> {
    return this.http.put<IFileType>(this.resourceUrl, fileType, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IFileType>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IFileType[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  search(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IFileType[]>(this.resourceSearchUrl, { params: options, observe: 'response' });
  }
}
