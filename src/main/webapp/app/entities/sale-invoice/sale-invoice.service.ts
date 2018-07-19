import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { ISaleInvoice } from 'app/shared/model/sale-invoice.model';

type EntityResponseType = HttpResponse<ISaleInvoice>;
type EntityArrayResponseType = HttpResponse<ISaleInvoice[]>;

@Injectable({ providedIn: 'root' })
export class SaleInvoiceService {
    private resourceUrl = SERVER_API_URL + 'api/sale-invoices';

    constructor(private http: HttpClient) {}

    create(saleInvoice: ISaleInvoice): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(saleInvoice);
        return this.http
            .post<ISaleInvoice>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    update(saleInvoice: ISaleInvoice): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(saleInvoice);
        return this.http
            .put<ISaleInvoice>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http
            .get<ISaleInvoice>(`${this.resourceUrl}/${id}`, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<ISaleInvoice[]>(this.resourceUrl, { params: options, observe: 'response' })
            .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    private convertDateFromClient(saleInvoice: ISaleInvoice): ISaleInvoice {
        const copy: ISaleInvoice = Object.assign({}, saleInvoice, {
            invoiceDate: saleInvoice.invoiceDate != null && saleInvoice.invoiceDate.isValid() ? saleInvoice.invoiceDate.toJSON() : null
        });
        return copy;
    }

    private convertDateFromServer(res: EntityResponseType): EntityResponseType {
        res.body.invoiceDate = res.body.invoiceDate != null ? moment(res.body.invoiceDate) : null;
        return res;
    }

    private convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
        res.body.forEach((saleInvoice: ISaleInvoice) => {
            saleInvoice.invoiceDate = saleInvoice.invoiceDate != null ? moment(saleInvoice.invoiceDate) : null;
        });
        return res;
    }
}
