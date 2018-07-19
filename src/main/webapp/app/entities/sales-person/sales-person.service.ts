import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { ISalesPerson } from 'app/shared/model/sales-person.model';

type EntityResponseType = HttpResponse<ISalesPerson>;
type EntityArrayResponseType = HttpResponse<ISalesPerson[]>;

@Injectable({ providedIn: 'root' })
export class SalesPersonService {
    private resourceUrl = SERVER_API_URL + 'api/sales-people';

    constructor(private http: HttpClient) {}

    create(salesPerson: ISalesPerson): Observable<EntityResponseType> {
        return this.http.post<ISalesPerson>(this.resourceUrl, salesPerson, { observe: 'response' });
    }

    update(salesPerson: ISalesPerson): Observable<EntityResponseType> {
        return this.http.put<ISalesPerson>(this.resourceUrl, salesPerson, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<ISalesPerson>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<ISalesPerson[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }
}
