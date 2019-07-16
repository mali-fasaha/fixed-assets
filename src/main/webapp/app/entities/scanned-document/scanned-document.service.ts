import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IScannedDocument } from 'app/shared/model/scanned-document.model';

type EntityResponseType = HttpResponse<IScannedDocument>;
type EntityArrayResponseType = HttpResponse<IScannedDocument[]>;

@Injectable({ providedIn: 'root' })
export class ScannedDocumentService {
  public resourceUrl = SERVER_API_URL + 'api/scanned-documents';
  public resourceSearchUrl = SERVER_API_URL + 'api/_search/scanned-documents';

  constructor(protected http: HttpClient) {}

  create(scannedDocument: IScannedDocument): Observable<EntityResponseType> {
    return this.http.post<IScannedDocument>(this.resourceUrl, scannedDocument, { observe: 'response' });
  }

  update(scannedDocument: IScannedDocument): Observable<EntityResponseType> {
    return this.http.put<IScannedDocument>(this.resourceUrl, scannedDocument, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IScannedDocument>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IScannedDocument[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  search(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IScannedDocument[]>(this.resourceSearchUrl, { params: options, observe: 'response' });
  }
}
