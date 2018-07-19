import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IPurchaseInvoiceItem } from 'app/shared/model/purchase-invoice-item.model';

type EntityResponseType = HttpResponse<IPurchaseInvoiceItem>;
type EntityArrayResponseType = HttpResponse<IPurchaseInvoiceItem[]>;

@Injectable({ providedIn: 'root' })
export class PurchaseInvoiceItemService {
    private resourceUrl = SERVER_API_URL + 'api/purchase-invoice-items';

    constructor(private http: HttpClient) {}

    create(purchaseInvoiceItem: IPurchaseInvoiceItem): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(purchaseInvoiceItem);
        return this.http
            .post<IPurchaseInvoiceItem>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    update(purchaseInvoiceItem: IPurchaseInvoiceItem): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(purchaseInvoiceItem);
        return this.http
            .put<IPurchaseInvoiceItem>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http
            .get<IPurchaseInvoiceItem>(`${this.resourceUrl}/${id}`, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<IPurchaseInvoiceItem[]>(this.resourceUrl, { params: options, observe: 'response' })
            .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    private convertDateFromClient(purchaseInvoiceItem: IPurchaseInvoiceItem): IPurchaseInvoiceItem {
        const copy: IPurchaseInvoiceItem = Object.assign({}, purchaseInvoiceItem, {
            expiaryDate:
                purchaseInvoiceItem.expiaryDate != null && purchaseInvoiceItem.expiaryDate.isValid()
                    ? purchaseInvoiceItem.expiaryDate.toJSON()
                    : null
        });
        return copy;
    }

    private convertDateFromServer(res: EntityResponseType): EntityResponseType {
        res.body.expiaryDate = res.body.expiaryDate != null ? moment(res.body.expiaryDate) : null;
        return res;
    }

    private convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
        res.body.forEach((purchaseInvoiceItem: IPurchaseInvoiceItem) => {
            purchaseInvoiceItem.expiaryDate = purchaseInvoiceItem.expiaryDate != null ? moment(purchaseInvoiceItem.expiaryDate) : null;
        });
        return res;
    }
}
