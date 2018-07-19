import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IPurchaseInvoice } from 'app/shared/model/purchase-invoice.model';

type EntityResponseType = HttpResponse<IPurchaseInvoice>;
type EntityArrayResponseType = HttpResponse<IPurchaseInvoice[]>;

@Injectable({ providedIn: 'root' })
export class PurchaseInvoiceService {
    private resourceUrl = SERVER_API_URL + 'api/purchase-invoices';

    constructor(private http: HttpClient) {}

    create(purchaseInvoice: IPurchaseInvoice): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(purchaseInvoice);
        return this.http
            .post<IPurchaseInvoice>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    update(purchaseInvoice: IPurchaseInvoice): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(purchaseInvoice);
        return this.http
            .put<IPurchaseInvoice>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http
            .get<IPurchaseInvoice>(`${this.resourceUrl}/${id}`, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<IPurchaseInvoice[]>(this.resourceUrl, { params: options, observe: 'response' })
            .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    private convertDateFromClient(purchaseInvoice: IPurchaseInvoice): IPurchaseInvoice {
        const copy: IPurchaseInvoice = Object.assign({}, purchaseInvoice, {
            invoiceDate:
                purchaseInvoice.invoiceDate != null && purchaseInvoice.invoiceDate.isValid() ? purchaseInvoice.invoiceDate.toJSON() : null
        });
        return copy;
    }

    private convertDateFromServer(res: EntityResponseType): EntityResponseType {
        res.body.invoiceDate = res.body.invoiceDate != null ? moment(res.body.invoiceDate) : null;
        return res;
    }

    private convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
        res.body.forEach((purchaseInvoice: IPurchaseInvoice) => {
            purchaseInvoice.invoiceDate = purchaseInvoice.invoiceDate != null ? moment(purchaseInvoice.invoiceDate) : null;
        });
        return res;
    }
}
