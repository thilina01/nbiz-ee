import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IBrand } from 'app/shared/model/brand.model';

type EntityResponseType = HttpResponse<IBrand>;
type EntityArrayResponseType = HttpResponse<IBrand[]>;

@Injectable({ providedIn: 'root' })
export class BrandService {
    private resourceUrl = SERVER_API_URL + 'api/brands';

    constructor(private http: HttpClient) {}

    create(brand: IBrand): Observable<EntityResponseType> {
        return this.http.post<IBrand>(this.resourceUrl, brand, { observe: 'response' });
    }

    update(brand: IBrand): Observable<EntityResponseType> {
        return this.http.put<IBrand>(this.resourceUrl, brand, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<IBrand>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IBrand[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }
}
