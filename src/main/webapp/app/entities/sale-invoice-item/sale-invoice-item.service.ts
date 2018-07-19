import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { ISaleInvoiceItem } from 'app/shared/model/sale-invoice-item.model';

type EntityResponseType = HttpResponse<ISaleInvoiceItem>;
type EntityArrayResponseType = HttpResponse<ISaleInvoiceItem[]>;

@Injectable({ providedIn: 'root' })
export class SaleInvoiceItemService {
    private resourceUrl = SERVER_API_URL + 'api/sale-invoice-items';

    constructor(private http: HttpClient) {}

    create(saleInvoiceItem: ISaleInvoiceItem): Observable<EntityResponseType> {
        return this.http.post<ISaleInvoiceItem>(this.resourceUrl, saleInvoiceItem, { observe: 'response' });
    }

    update(saleInvoiceItem: ISaleInvoiceItem): Observable<EntityResponseType> {
        return this.http.put<ISaleInvoiceItem>(this.resourceUrl, saleInvoiceItem, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<ISaleInvoiceItem>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<ISaleInvoiceItem[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }
}
