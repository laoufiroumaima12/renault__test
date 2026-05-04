export interface PageRequest {
    page: number;
    size: number;
    sortBy?: string;
    sortDirection?: 'ASC' | 'DESC';
}