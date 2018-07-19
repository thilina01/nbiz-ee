import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { ISaleInvoicePayment } from 'app/shared/model/sale-invoice-payment.model';

type EntityResponseType = HttpResponse<ISaleInvoicePayment>;
type EntityArrayResponseType = HttpResponse<ISaleInvoicePayment[]>;

@Injectable({ providedIn: 'root' })
export class SaleInvoicePaymentService {
    private resourceUrl = SERVER_API_URL + 'api/sale-invoice-payments';

    constructor(private http: HttpClient) {}

    create(saleInvoicePayment: ISaleInvoicePayment): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(saleInvoicePayment);
        return this.http
            .post<ISaleInvoicePayment>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    update(saleInvoicePayment: ISaleInvoicePayment): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(saleInvoicePayment);
        return this.http
            .put<ISaleInvoicePayment>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http
            .get<ISaleInvoicePayment>(`${this.resourceUrl}/${id}`, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<ISaleInvoicePayment[]>(this.resourceUrl, { params: options, observe: 'response' })
            .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    private convertDateFromClient(saleInvoicePayment: ISaleInvoicePayment): ISaleInvoicePayment {
        const copy: ISaleInvoicePayment = Object.assign({}, saleInvoicePayment, {
            paymentDate:
                saleInvoicePayment.paymentDate != null && saleInvoicePayment.paymentDate.isValid()
                    ? saleInvoicePayment.paymentDate.toJSON()
                    : null
        });
        return copy;
    }

    private convertDateFromServer(res: EntityResponseType): EntityResponseType {
        res.body.paymentDate = res.body.paymentDate != null ? moment(res.body.paymentDate) : null;
        return res;
    }

    private convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
        res.body.forEach((saleInvoicePayment: ISaleInvoicePayment) => {
            saleInvoicePayment.paymentDate = saleInvoicePayment.paymentDate != null ? moment(saleInvoicePayment.paymentDate) : null;
        });
        return res;
    }
}
