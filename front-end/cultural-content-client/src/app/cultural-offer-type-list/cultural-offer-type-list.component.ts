import { Component, Input, OnInit, Output, OnChanges, ViewChild } from '@angular/core';
import { CulturalOfferType} from '../model/cultural-offer-type';
import {MatTableDataSource} from '@angular/material/table';
import { EventEmitter } from '@angular/core';
import {MatPaginator} from '@angular/material/paginator';

@Component({
  selector: 'app-cultural-offer-type-list',
  templateUrl: './cultural-offer-type-list.component.html',
  styleUrls: ['./cultural-offer-type-list.component.scss']
})
export class CulturalOfferTypeListComponent implements OnInit {

  @Input()
  offerTypes: CulturalOfferType[];

  @Output()
  onCreateTypeEvent = new EventEmitter<any>();
  @Output()
  onDeleteTypeEvent = new EventEmitter<any>();
  @Output()
  onUpdateTypeEvent = new EventEmitter<any>();

  displayedColumns: string[] = ['name', 'options'];
  @ViewChild(MatPaginator) paginator: MatPaginator;

  dataSource: MatTableDataSource<CulturalOfferType>;

  constructor() { }

  ngOnChanges(): void{
    this.dataSource = new MatTableDataSource<CulturalOfferType>(this.offerTypes);
  }

  ngOnInit(): void {
    this.dataSource = new MatTableDataSource<CulturalOfferType>(this.offerTypes);
    this.dataSource.paginator = this.paginator;
  }

  createType(): void{
    this.onCreateTypeEvent.emit("dldkls");
  }

  deleteType(): void{

  }

  updateType(element: any): void{
    this.onUpdateTypeEvent.emit(element);
  }

}
