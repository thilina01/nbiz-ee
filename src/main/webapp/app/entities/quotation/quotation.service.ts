import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IQuotation } from 'app/shared/model/quotation.model';

type EntityResponseType = HttpResponse<IQuotation>;
type EntityArrayResponseType = HttpResponse<IQuotation[]>;

@Injectable({ providedIn: 'root' })
export class QuotationService {
    private resourceUrl = SERVER_API_URL + 'api/quotations';

    constructor(private http: HttpClient) {}

    create(quotation: IQuotation): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(quotation);
        return this.http
            .post<IQuotation>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    update(quotation: IQuotation): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(quotation);
        return this.http
            .put<IQuotation>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http
            .get<IQuotation>(`${this.resourceUrl}/${id}`, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<IQuotation[]>(this.resourceUrl, { params: options, observe: 'response' })
            .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    private convertDateFromClient(quotation: IQuotation): IQuotation {
        const copy: IQuotation = Object.assign({}, quotation, {
            quotationDate: quotation.quotationDate != null && quotation.quotationDate.isValid() ? quotation.quotationDate.toJSON() : null
        });
        return copy;
    }

    private convertDateFromServer(res: EntityResponseType): EntityResponseType {
        res.body.quotationDate = res.body.quotationDate != null ? moment(res.body.quotationDate) : null;
        return res;
    }

    private convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
        res.body.forEach((quotation: IQuotation) => {
            quotation.quotationDate = quotation.quotationDate != null ? moment(quotation.quotationDate) : null;
        });
        return res;
    }
}
